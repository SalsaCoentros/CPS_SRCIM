package Resource;

import Utilities.Constants;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class ResponderResourceClear extends AchieveREResponder {

    public ResponderResourceClear(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) {
        ACLMessage msg = request.createReply();
        msg.setPerformative(ACLMessage.INFORM);
        if (request.getOntology().equals(Constants.ONTOLOGY_CLEAR_RESOURCE)) {
            if(request.getContent().equals("true"))
                ((ResourceAgent) myAgent).reservedTo = null;
        }
        return msg;
    }
}
