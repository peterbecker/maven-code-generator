package ${package};

public class ${entity.name} {
<#list entity.property as property>
    private ${property.type} ${property.name};

</#list>
    public ${entity.name}(<#list entity.property as property>${property.type} ${property.name}<#if property_has_next>, </#if></#list>) {
<#list entity.property as property>
        this.${property.name} = ${property.name};
</#list>
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