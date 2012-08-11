package ${package};

import javax.persistence.EntityManager;
import maven.code.generator.AbstractDao;

public class ${entity.name}Dao extends AbstractDao<${entity.name}> {
    public ${entity.name}Dao(EntityManager em) {
        super(em, ${entity.name}.class);
    }
}