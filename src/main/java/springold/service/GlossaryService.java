package springold.service;

import springold.bean.Glossary;

import java.util.List;

public interface GlossaryService {
    List<Glossary> findByCategory(short s);
    String getByCategoryAndCode(Short category,int code);
}
