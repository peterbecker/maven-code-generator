package ${package};

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ${entity.name} {
    @Id
    private long id;

<#list entity.property as property>
    private ${property.type} ${property.name};

</#list>
    public ${entity.name}() {
    }

    public ${entity.name}(<#list entity.property as property>${property.type} ${property.name}<#if property_has_next>, </#if></#list>) {
<#list entity.property as property>
        this.${property.name} = ${property.name};
</#list>
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
<#list entity.property as property>

    public ${property.type} get${property.name?cap_first}() {
        return ${property.name};
    }

    public void set${property.name?cap_first}(${property.type} ${property.name}) {
        this.${property.name} = ${property.name};
    }
</#list>
}