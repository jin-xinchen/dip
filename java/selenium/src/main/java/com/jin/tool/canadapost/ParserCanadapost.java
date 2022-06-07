package com.jin.tool.canadapost;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ParserCanadapost {
    public static List<Group> getGroupsFromXML(String strXML) throws DocumentException {
        List<Group> gs = new ArrayList<Group>();
        Document doc = null;
        if(strXML==null || strXML.isBlank() || strXML.isEmpty()){
            return gs;
        }else {
            doc = DocumentHelper.parseText(strXML);
            Element root = doc.getRootElement();
            Iterator it = root.elementIterator();
            while (it.hasNext()) {
                Element group = (Element) it.next();
//                System.out.println(group.getName() + " : " + group.getTextTrim());
                Element link = group.element("link");
                Group g = new Group();
                g.href= link.attribute("href").getValue();
                g.mediaType=link.attribute("media-type").getValue();
                g.rel=link.attribute("rel").getValue();
                g.groupId =group.element("group-id").getTextTrim();
                gs.add(g);
//                System.out.println(g.toXML());
            }
        }
        return gs;
    }

}
