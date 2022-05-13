package Resource;

import jade.core.AID;
import jade.core.Agent;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import Libraries.IResource;
import jade.domain.FIPAException;
import Utilities.Constants;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author Ricardo Silva Peres <ricardo.peres@uninova.pt>
 */
public class ResourceAgent extends Agent {

    String id;
    IResource myLib;
    String description;
    String[] associatedSkills;
    String location;
    boolean reserved = false;
    String reservedSkill = null;
    AID reservedTo = null;

    @Override
    protected void setup() {
        Object[] args = this.getArguments();
        this.id = (String) args[0];
        this.description = (String) args[1];

        //Load hw lib
        try {
            String className = "Libraries." + (String) args[2];
            Class cls = Class.forName(className);
            Object instance;
            instance = cls.newInstance();
            myLib = (IResource) instance;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ResourceAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.location = (String) args[3];

        myLib.init(this);
        this.associatedSkills = myLib.getSkills();
        System.out.println("Resource Deployed: " + this.id + " Executes: " + Arrays.toString(associatedSkills));

        try{
            Utilities.DFInteraction.RegisterInDF(this, this.associatedSkills, Constants.DFSERVICE_RESOURCE);
        } catch(FIPAException ex){
            ex.printStackTrace();
        }
        this.addBehaviour(new OfferSkill(this, MessageTemplate.MatchPerformative(ACLMessage.CFP)));
        this.addBehaviour(new SkillExecutionResponse(this, MessageTemplate.MatchOntology(Constants.ONTOLOGY_EXECUTE_SKILL)));
        this.addBehaviour(new ResponderResourceClear(this, MessageTemplate.MatchOntology(Constants.ONTOLOGY_CLEAR_RESOURCE)));
        /*this.addBehaviour(new SkillExecutionResponse(this, MessageTemplate.MatchPerformative((ACLMessage.REQUEST))));
        this.addBehaviour(new ResponderResourceClear(this, MessageTemplate.MatchPerformative((ACLMessage.REQUEST))));*/
    }

    @Override
    protected void takeDown() {
        super.takeDown(); 
    }
}
