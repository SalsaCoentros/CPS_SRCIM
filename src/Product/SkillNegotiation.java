package Product;

import Utilities.Constants;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;

public class SkillNegotiation extends ContractNetInitiator {

    public SkillNegotiation(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected void handleInform(ACLMessage inform) {
        //System.out.println(myAgent.getLocalName() + ": INFORM message received");
    }

    @Override
    protected void handleAllResponses (Vector response, Vector acceptances) {

        int best_proposal = 0;
        int best_resource = -1;
        int best_freq = 0;
        //System.out.println(myAgent.getLocalName() + ": ALL PROPOSALS received");

        for (int i = 0; i<response.size(); i++) {
             ACLMessage msg = (ACLMessage)response.get(i);

             if (msg.getPerformative() == ACLMessage.PROPOSE) { //if their response is a proposition
                 //System.out.println(msg.getSender().getLocalName() + " sent a proposition.");

                 StringTokenizer content = new StringTokenizer(msg.getContent(), Constants.TOKEN);
                 String performance_value = content.nextToken();
                 String location = content.nextToken();
                 String skill1 = content.nextToken();
                 String skill2 = null;

                 if(content.hasMoreElements()) {
                     skill2 = content.nextToken();
                 }

                 int freq = Collections.frequency(((ProductAgent)myAgent).executionPlan,skill1);

                 if ( skill2 != null)
                     freq = freq + Collections.frequency(((ProductAgent)myAgent).executionPlan,skill2);

                 int proposal_value = Integer.parseInt(performance_value);

                 if (best_proposal == 0) {
                     best_proposal = proposal_value;
                     best_resource = i;
                     best_freq = freq;
                     ((ProductAgent)myAgent).nextLocation = location;
                 }

                 if (proposal_value < best_proposal && freq >= best_freq) {
                     best_resource = i;
                     best_proposal = proposal_value;
                     best_freq = freq;
                     ((ProductAgent)myAgent).nextLocation = location;
                 }

             }
        }

        if (best_resource != -1) {

            for (int i = 0; i < response.size(); i++) {
                ACLMessage msg = (ACLMessage) response.get(i);
                ACLMessage reply = msg.createReply();

                if (i == best_resource)  // SEND ACCEPT
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);

                else // SEND REFUSE
                    reply.setPerformative((ACLMessage.REJECT_PROPOSAL));

                acceptances.add(reply);
            }

            ((ProductAgent) myAgent).skillReserved = true;

            String agentName = ((ACLMessage)response.get(best_resource)).getSender().getLocalName();

            ((ProductAgent)myAgent).msgExecuteSkill.addReceiver(new AID(agentName,false));
            ((ProductAgent)myAgent).msgExecuteSkill.setContent(((ProductAgent)myAgent).currentSkill);
        }

    }

}
