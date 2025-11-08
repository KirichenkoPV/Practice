package Practice_04_10_2025.behs;

import Practice_04_10_2025.behs.Utils.BidResponse;
import Practice_04_10_2025.behs.Utils.DfUtils;
import Practice_04_10_2025.behs.Utils.JsonUtils;
import Practice_04_10_2025.behs.Utils.PriceProposal;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Seller extends Behaviour {
    private MessageTemplate mt;
    private boolean proposalsSent = false;
    private Map<AID, Double> receivedBids = new HashMap<>(); // храним ставки от покупателей
    private List<AID> buyers; // список покупателей

    @Override
    public void onStart(){
        mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
        log.info("Продавец {} начал торги", myAgent.getLocalName());
    }

    @Override
    public void action(){
        if (!proposalsSent) {
            sendPriceProposals();
            proposalsSent = true;
        } else {
            ACLMessage m = myAgent.receive(mt);
            if (m != null) {
                // Correctly handle the Optional
                Optional<BidResponse> optionalResponse = JsonUtils.fromJson(m.getContent(), BidResponse.class);

                if (optionalResponse.isPresent()) {
                    BidResponse response = optionalResponse.get(); // Extract the actual object
                    receivedBids.put(m.getSender(), response.getBid());
                    log.info("Продавец получил ставку {} от {}", response.getBid(), m.getSender().getLocalName());

                    if (receivedBids.size() == buyers.size()) {
                        chooseWinner();
                    }
                } else {
                    log.error("Не удалось декодировать ставку от {}", m.getSender().getLocalName());
                }
            } else {
                block();
            }
        }
    }

    private void sendPriceProposals() {
        buyers = DfUtils.search(myAgent, "Bayer");
        log.info("Найдено покупателей: {}", buyers.size());

        // Генерируем случайную стартовую цену (например, от 100 до 1000)
        double startPrice = 100 + Math.random() * 900;

        for (AID buyer : buyers) {
            ACLMessage msg = new ACLMessage(ACLMessage.CFP);
            msg.addReceiver(buyer);
            msg.setContent(JsonUtils.toJson(new PriceProposal(startPrice)));
            myAgent.send(msg);
            log.info("Отправлено предложение покупателю {} со стартовой ценой: {}",
                    buyer.getLocalName(), startPrice);
        }
    }

    private void chooseWinner() {
        // Находим победителя (максимальная ставка)
        Map.Entry<AID, Double> winner = Collections.max(
                receivedBids.entrySet(),
                Map.Entry.comparingByValue()
        );

        log.info("ПОБЕДИТЕЛЬ: {} со ставкой {}",
                winner.getKey().getLocalName(), winner.getValue());

        // Отправляем сообщение победителю
        ACLMessage winMsg = new ACLMessage(ACLMessage.INFORM);
        winMsg.addReceiver(winner.getKey());
        winMsg.setContent("Поздравляем! Вы выиграли торги со ставкой: " + winner.getValue());
        myAgent.send(winMsg);

        // Можно также отправить сообщения проигравшим
        for (AID buyer : buyers) {
            if (!buyer.equals(winner.getKey())) {
                ACLMessage loseMsg = new ACLMessage(ACLMessage.INFORM);
                loseMsg.addReceiver(buyer);
                loseMsg.setContent("К сожалению, вы не выиграли торги. Победившая ставка: " + winner.getValue());
                myAgent.send(loseMsg);
            }
        }
    }

    @Override
    public boolean done(){
        return receivedBids.size() == buyers.size() && buyers != null && !buyers.isEmpty();
    }
}