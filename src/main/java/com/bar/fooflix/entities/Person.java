package com.bar.fooflix.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

import java.util.Date;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NodeEntity
@JsonPropertyOrder({
})
public class Person {
    @GraphId
    Long nodeId;
    @Indexed(unique = true)
    String id;

    @JsonProperty("name")
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "people")
    String name;

    @JsonProperty("birthday")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date birthday;

    @JsonProperty("birthplace")
    private String birthplace;

    @JsonIgnore
    private String biography;

    @JsonIgnore
    private Integer version;

    @JsonIgnore
    private Date lastModified;

    protected Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Person() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("birthday")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    public Date getBirthday() {
        return birthday;
    }

    @JsonProperty("birthday")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @JsonProperty("birthplace")
    public String getBirthplace() {
        return birthplace;
    }

    @JsonProperty("birthplace")
    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;
        if (nodeId == null) return super.equals(o);
        return nodeId.equals(person.nodeId);

    }

    @Override
    public int hashCode() {
        return nodeId != null ? nodeId.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", name, id);
    }
}