import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil;

import java.util.HashMap;
def Message processData(Message message) {
    def body = message.getBody(String)
    def headers = message.getHeaders()
    def properties = message.getProperties()

    message.setBody(DoMapping(body, headers, properties))

    return message
}
def DoMapping(String body, Map headers, Map properties) {
    def tree = new JsonSlurper().parseText(body);
    def fixedTree = fixWhitespacesInTree(tree);
    JsonBuilder builder = new JsonBuilder(fixedTree);
    return builder;
}

def fixWhitespacesInTree(def tree) {
    switch (tree) {
        case Map:
            return tree.collectEntries { k, v ->
                [(k.replaceAll('\\s+', '_')):fixWhitespacesInTree(v)]
            }
        case Collection:
            return tree.collect { e -> fixWhitespacesInTree(e) }
        default :
            return tree
    }
}
TestRun()

void TestRun() {
    def scriptDir = new File(getClass().protectionDomain.codeSource.location.toURI().path).parent
    def dataDir = scriptDir + "\\Data"

    Map headers = [:]
    Map props = [:]

    File inputFile = new File("$dataDir\\json.txt")
    File outputFile = new File("$dataDir\\json_output.txt")
    //File inputFile = new File("$dataDir\\csv_complex.txt")
    //File outputFile = new File("$dataDir\\csv_complex_xml_output.txt")

    def inputBody = inputFile.getText("UTF-8")
    def outputBody = DoMapping(inputBody, headers, props)

    println outputBody
    //outputFile.write outputBody
}
