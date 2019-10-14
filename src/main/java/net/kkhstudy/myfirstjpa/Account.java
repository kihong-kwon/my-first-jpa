package net.kkhstudy.myfirstjpa;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String passwrod;

    @OneToMany(mappedBy = "owner")
    private Set<Study> studies = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    // 속성이 같은 항목을 별도의 클래스로 관리, 테이블이 생성될때 해당 오브젝트의 항목들이 컬럼으로 추가된다.
    @Embedded
    @AttributeOverrides({
            // 오브젝트의 특정 항목이름을 변경해서 테이블에 등록할 수 있다.
            @AttributeOverride(name = "street", column = @Column(name = "homeStreet"))
    })
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswrod() {
        return passwrod;
    }

    public void setPasswrod(String passwrod) {
        this.passwrod = passwrod;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }

    public void addStudy(Study study) {
        this.getStudies().add(study);
        study.setOwner(this);
    }

    public void removeStudy(Study study) {
        this.getStudies().remove(study);
        study.setOwner(null);
    }
}
