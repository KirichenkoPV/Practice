package Practice_04_10_2025.behs;


import Practice_04_10_2025.behs.Utils.JsonUtils;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bayer extends Behaviour {
    private MessageTemplate mt;
    @Override
    public void onStart(){
        mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        log.info("I am {} and I am start",myAgent.getLocalName());

    }
    @Override
    public void action(){
        log.info("I am {} and I action", myAgent.getLocalName());
        ACLMessage m = myAgent.receive(mt);
        if (m != null) {
            log.info("Received Ping msg from {} with {}", m.getSender().getLocalName(), m.getContent());
            ACLMessage answer = new ACLMessage(ACLMessage.PROPOSE);
            answer.addReceiver(m.getSender());
            answer.setContent(JsonUtils.toJson("Good Bay"));
            myAgent.send(answer);
            log.info("Sent pong to {}", m.getSender().getLocalName());
        } else {
            block();
        }

    }
    @Override
    public boolean done(){
        log.info("I am {} and I am stopped", myAgent.getLocalName());
        return false;
    }
}
