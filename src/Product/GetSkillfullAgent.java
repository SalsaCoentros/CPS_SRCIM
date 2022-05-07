package Product;

import Utilities.Constants;
import Utilities.DFInteraction;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;

public class GetSkillfullAgent extends SimpleBehaviour {

    private boolean finished = false;

    public GetSkillfullAgent(Agent a){}


    @Override
    public void action() {
        ((ProductAgent)myAgent).cfp.clearAllReceiver();
        ((ProductAgent)myAgent).cfp.clearAllReplyTo();

        getCompetentAgents();

        finished = true;
    }

    @Override
    public boolean done() {
        return finished;
    }

    // Returns a list of agents registered in the DF with a given skill
    private void getCompetentAgents () {
        //ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        DFAgentDescription[] SkillfulAgents = null;
        String askedSkill;

        if (!((ProductAgent) myAgent).skillReserved) {
            askedSkill = ((ProductAgent) myAgent).currentSkill;
            ((ProductAgent)myAgent).cfp.setContent(askedSkill);
        }
        else {
            askedSkill = "sk_move";
            ((ProductAgent)myAgent).cfp.setContent(((ProductAgent)myAgent).currentLocation + Constants.TOKEN + ((ProductAgent)myAgent).nextLocation);
        }

        try {
            SkillfulAgents = DFInteraction.SearchInDFByName(askedSkill, myAgent);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        assert SkillfulAgents != null;
        if ( SkillfulAgents.length != 0 ) {
            //System.out.println("List of agents that can execute the skill: ");
            for (DFAgentDescription skillfulAgent : SkillfulAgents) {
                //System.out.println(SkillfulAgents[i].getName().getLocalName());
                ((ProductAgent) myAgent).cfp.addReceiver(skillfulAgent.getName());
            }
        } else {
            System.out.println("There are no agents with the following skill: " + ((ProductAgent)myAgent).currentSkill);
            // we'll have to abort in this case
        }

    }


}
