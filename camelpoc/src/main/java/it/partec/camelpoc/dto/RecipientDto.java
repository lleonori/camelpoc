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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RecipientDto implements Serializable {

    private static final long serialVersionUID = 5851038813219503043L;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("body")
    private String body;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}