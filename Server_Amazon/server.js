var api = require('./index').api
var express = require('express');
var app = express();
const puppeteer = require('puppeteer');
var browser 


app.get('/onca/xml',async function (req, res) {
  if(!browser){
    browser = await puppeteer.launch();
  }
  var find = req.query.Keywords
  res.type('application/xml');
  res.send(await api(browser,find))
});

app.listen(3000, function () {
  console.log('Api Amazon ready!');
});