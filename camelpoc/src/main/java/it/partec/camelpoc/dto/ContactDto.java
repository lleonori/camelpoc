//package it.partec.camelpoc.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
//import org.apache.camel.dataformat.bindy.annotation.DataField;
//
//@CsvRecord(separator = ";")
//@Data
//@Builder(toBuilder = true)
//@NoArgsConstructor
//@AllArgsConstructor
//public class ContactDto {
//
//    @DataField(pos = 1)
//    private Integer id;
//    @DataField(pos = 2)
//    private Boolean flagContact;
//    @DataField(pos = 3)
//    private String name;
//}


package it.partec.camelpoc.dto;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ";")
public class ContactDto {

    @DataField(pos = 1)
    private Integer id;

    @DataField(pos = 2)
    private Boolean flagContact;

    @DataField(pos = 3)
    private String name;

    public ContactDto() {
        // Default constructor
    }

    public ContactDto(Integer id, Boolean flagContact, String name) {
        this.id = id;
        this.flagContact = flagContact;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getFlagContact() {
        return flagContact;
    }

    public void setFlagContact(Boolean flagContact) {
        this.flagContact = flagContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
