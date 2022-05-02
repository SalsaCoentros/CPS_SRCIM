package Product;

import Utilities.DFInteraction;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class GetSkillfullAgent extends SimpleBehaviour {

    private boolean finished = false;

    public GetSkillfullAgent(Agent a){};


    @Override
    public void action() {
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
        DFAgentDescription [] SkillfulAgents = null;

        try {
            SkillfulAgents = DFInteraction.SearchInDFByName(((ProductAgent)myAgent).currentSkill,myAgent);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        if ( SkillfulAgents.length != 0 ) {
            System.out.println("List of agents that can execute the skill " + ((ProductAgent)myAgent).currentSkill + " : ");
            for (int i = 0; i < SkillfulAgents.length; i++) {
                System.out.println(SkillfulAgents[i].getName().getLocalName());
                ((ProductAgent)myAgent).cfp.addReceiver(SkillfulAgents[i].getName());
            }
        } else {
            System.out.println("There are no agents with the following skill: " + ((ProductAgent)myAgent).currentSkill);
            // we'll have to abort in this case
        }
    }


}
