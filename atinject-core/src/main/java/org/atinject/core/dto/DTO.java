package org.atinject.core.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="_class")
public abstract class DTO implements Serializable {

    private static final long serialVersionUID = 1L;

}
