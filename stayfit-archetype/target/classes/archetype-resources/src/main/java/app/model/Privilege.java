package ${package}.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.*;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "privileges")
public class Privilege implements GrantedAuthority {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;

	@Override
	public String getAuthority() {
		return name;
	}

}
