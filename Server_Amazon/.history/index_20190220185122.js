async function api(browser, find) {
  
  const page = await browser.newPage();
  await page.goto('https://www.amazon.it/s/ref=nb_sb_noss_2?__mk_it_IT=%C3%85M%C3%85%C5%BD%C3%95%C3%91&url=search-alias%3Daps&field-keywords='+find);

  // Get the "viewport" of the page, as reported by the page.
  const result = await page.evaluate(() => {
    let ul = document.querySelector("#s-results-list-atf")
    let li = ul.querySelectorAll(".s-result-item")
    var doc = document.implementation.createDocument("", "", null);
    var parent = doc.createElement("Items")
    for( var i = 0 ; i < li.length; i++){
         
        let title = li[i].querySelector(".s-access-detail-page")
        alert(title);
        if (title){
            var item = doc.createElement("Item")
            var url = doc.createElement("DetailPageURL")
            let link =  "https://www.amazon.it" + li[i].querySelector(".s-access-detail-page").getAttribute("href")
            url.textContent= link
            item.appendChild(url)
            var itemattributes = doc.createElement("ItemAttributes")
            var titles = doc.createElement("Title") 
            title = title.getAttribute("title")
            titles.textContent = title
            itemattributes.appendChild(titles)

            let brand = li[i].querySelector(".s-access-detail-page").parentNode.parentNode.querySelectorAll(".a-color-secondary")[1]

            if (brand){
              brand = brand.textContent
              var marca = doc.createElement("Brand")
              marca.textContent = brand
              itemattributes.appendChild(marca)

            }
            var images = doc.createElement("Img")
            let image = li[i].querySelector(".s-access-image").getAttribute("src")
            images.textContent = image
            itemattributes.appendChild(images)
            let price = li[i].querySelector(".s-price")
            if(!price){
              price = li[i].querySelector(".a-color-price")
            }
            if(price){
              price = price.textContent.split(" ")
              var listprice = doc.createElement("ListPrice")
              var amount = doc.createElement("Amount")   
              let value = price[1].replace(",", ".")
              amount.textContent = value
              listprice.appendChild(amount)
              var valuta = doc.createElement("Currency")
              let currency = price[0]
              valuta.textContent = currency
              listprice.appendChild(valuta)
              itemattributes.appendChild(listprice)  
            }
          
            item.appendChild(itemattributes)
            parent.appendChild(item) 
        } 
        
    }

    doc.appendChild(parent)
    var oSerializer = new XMLSerializer();
    var sXML = oSerializer.serializeToString(doc);
   
    return sXML
    
  });
  return result
};
module.exports = {api: api};