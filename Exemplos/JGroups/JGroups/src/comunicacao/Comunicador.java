package comunicacao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

public class Comunicador extends ReceiverAdapter {

    JChannel channel;
    List<Address> listaMembros;
    String frase;
    Message mensagem;
    JFrame_chatJGROUPS meuFrame;
    StringBuffer membrosStringBuffer;
    
    // 1. Lista que armazena o histórico global de mensagens na memória
    private final List<String> historicoGlobal = new ArrayList<>();

    public void iniciar(JFrame_chatJGROUPS meuFrame) throws Exception {
        this.channel = new JChannel();
        this.channel.setReceiver(this);
        this.meuFrame = meuFrame;
        
        this.channel.setName(meuFrame.getjTextField_apelido().getText());
        this.channel.connect(meuFrame.getTitle());
        
        // 2. Pede o estado atual (histórico) para os membros antigos do grupo assim que conecta
        // O primeiro parâmetro é o alvo (null escolhe o coordenador) e o segundo é o timeout em ms
        this.channel.getState(null, 10000);
    }

    public void enviar(String frase, String participante) {
        try {
            if (participante == null) {
                this.mensagem = new Message(null, frase);
            } else {
                for (int i = 0; i < this.listaMembros.size(); i++) {
                    if (participante.equals(listaMembros.get(i).toString())) {
                        this.mensagem = new Message(listaMembros.get(i), frase);
                        break;
                    }
                }
            }
            this.channel.send(this.mensagem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(meuFrame, "Algo ocorreu de errado ao enviar sua mensagem!!");
        }
    }

    public void finalizar() {
        this.channel.close();
    }

    @Override
    public void receive(Message msg) {
        Date dt = new Date();
        String linhaMensagem = "[" + dt.toString() + "] " + msg.getSrc() + " disse: " + msg.getObject().toString() + "\n";
        
        // 3. Adiciona a mensagem no histórico global e atualiza a tela
        synchronized (historicoGlobal) {
            historicoGlobal.add(linhaMensagem);
        }
        
        this.meuFrame.getjTextArea_mensagensGerais().append(linhaMensagem);
    }

    @Override
    public void viewAccepted(View view_atual) {
        this.listaMembros = view_atual.getMembers();
        this.membrosStringBuffer = new StringBuffer();
        
        this.meuFrame.getjTextArea_listaMembros().setText("");
        this.meuFrame.getjComboBox_listaParticipantesGrupo().removeAllItems();
        this.meuFrame.getjComboBox_listaParticipantesGrupo().addItem("Selecione o participante");
        
        for (int i = 0; i < listaMembros.size(); i++) {
            membrosStringBuffer.append(listaMembros.get(i) + "\n");
            this.meuFrame.getjComboBox_listaParticipantesGrupo().addItem(listaMembros.get(i).toString());
        }
        
        this.meuFrame.getjTextArea_listaMembros().setText(membrosStringBuffer.toString());
        
        // Nota: O histórico não precisa ser resgatado manualmente aqui via getText() 
        // porque o setState() e o receive() agora gerenciam o conteúdo do JTextArea.
    }

    /*
     * 4. Callback chamado quando UM NOVO MEMBRO entra e pede o histórico.
     * O nó existente envia a lista completa de mensagens através do OutputStream.
     */
    @Override
    public void getState(OutputStream output) throws Exception {
        synchronized (historicoGlobal) {
            Util.objectToStream(historicoGlobal, new DataOutputStream(output));
        }
    }

    /*
     * 5. Callback chamado NO NOVO MEMBRO para carregar o histórico recebido 
     * e exibi-lo na tela pela primeira vez.
     */
    @Override
    public void setState(InputStream input) throws Exception {
        List<String> listaRecebida;
        synchronized (historicoGlobal) {
            listaRecebida = (List<String>) Util.objectFromStream(new DataInputStream(input));
            historicoGlobal.clear();
            historicoGlobal.addAll(listaRecebida);
            
            // Limpa e reconstrói o JTextArea com o histórico recuperado do grupo
            StringBuilder sb = new StringBuilder();
            for (String m : historicoGlobal) {
                sb.append(m);
            }
            this.meuFrame.getjTextArea_mensagensGerais().setText(sb.toString());
        }
    }

    @Override
    public void suspect(Address mbr) {
        JOptionPane.showMessageDialog(meuFrame, "PROCESSO SUSPEITO DE FALHA: " + mbr);
    }
}