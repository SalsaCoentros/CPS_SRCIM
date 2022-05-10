package Resource;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

import java.util.Collections;
import java.util.List;

public class SkillExecutionResponse extends AchieveREResponder {
    public SkillExecutionResponse(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    @Override
    protected ACLMessage handleRequest (ACLMessage request) {
        String skill = request.getContent();
        ACLMessage msg = request.createReply();
        if (Collections.frequency(List.of((((ResourceAgent) myAgent).associatedSkills)),skill) == 1) {
            msg.setPerformative(ACLMessage.AGREE);
        } else {
            msg.setPerformative(ACLMessage.REFUSE);
        }
        return msg;
    }

    @Override
    protected ACLMessage prepareResultNotification (ACLMessage request, ACLMessage response) {
        ((ResourceAgent)myAgent).reserved = false;
        System.out.println(myAgent.getLocalName() + ": is starting the skill named " + ((ResourceAgent)myAgent).reservedSkill);
        //((ResourceAgent) myAgent).myLib.executeSkill(((ResourceAgent) myAgent).reservedSkill);
        block(2000);
        ACLMessage msg = request.createReply();
        msg.setPerformative(ACLMessage.INFORM);
        return msg;
    }
}
