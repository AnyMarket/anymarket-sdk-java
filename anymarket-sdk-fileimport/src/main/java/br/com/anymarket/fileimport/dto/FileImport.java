package br.com.anymarket.fileimport.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileImport {
    private Long id;

    public Long getId() {
        return id;
    }
}
