## Aula 1
 - Processo de avaliação
    - 20% nota é participação efetiva
    - 20% nota são notas de aula (no Github pessoal)
        - criar um arquivo chamado notas_aula.md
    - 60% da nota entre prova e trabalhos práticos
 - Apresentação e discussão do plano de ensino
 - Conceitos básicos de SD: comunicação, arquitetura, processamento concomitante versus paralelo, cluster versus grid

 * Sistemas Distribuídos:
    - Compartilhamento de recursos de hardware (Processador (CPU) e memória)
    - Sincronismo (para compartilhar é necessário sincronismo)
        - Relógio: lógico e físico
        - Recurso: Exclusão mutua
    - Dependentes do Sistema Operacional

 * Escrever precede o Ler

 - Comunicação
    - broadcast, multicast, unicast
    - é bloqueante: escrever (writer ou sender) e ler (reader ou receiver)
    - respeita ou segue o modelo TCP/IP (Aplicação, transporte, interface, rede)
        - endereço IP: servidor, cliente, grupo
        - máscara ou classe de rede e domínio
        - socket
        - porta lógica
        - programação Thread
        - Diferença de processamento concomitante vs processamento paralelo:
            * Concomitante (Processamento Concorrente)
                - Grid Computacional
                - Fracamente acoplados
                - CPU
                - Um processador alterna rapidamente entre tarefas, dando a ilusão que os processos ocorrem ao mesmo tempo
                - Sistemas Heterogêneos
                - Baixo Desempenho

            * Paralelo
                - Cluster Computacional
                - Fortemente Acoplados
                - GPU e mulit-cores
                - um processador (com multiplos núcleos) executa varias tarefas ao mesmo tempo
                - Sistemas Homogêneos
                - Alto Desempenho

        - Arquitetura
            - Cliente-servidor
            - ponto-a-ponto (peer-to-peer)

 - Thread
    - Sub ou Mini processo dentro do processo que encapsula tarefas
    - Seção critica: onde ocorre a escrita
    - Tipos:
        - Com Memória compartilhada (Seção Critica)
            - Sincronismo (Coordenação)
                - Semáforo ~= Tempo
                - Monitor = Ocupação
                - Programador tem que garantir o sincronismo
        - Sem Memória compartilhada
    - Tem a finalidade de garantir o processamento concomitante
    - Possui comandos para garantir o Sincronismo do Processo
    - Em Java é processamento concomitante (JVM)
        - Compartilhamento de mémoria -> Interface Runnable
        - Sem compartilhamento -> Classe Thread
        - Sincronização via synchornized, Lock, Semaphore, entre outros
        - é o mesmo para C#

    - Processo x Thread
        * Processo:
        - Instancia do programa em execução
        - Possui proprio espaço de memoria
            - Tem isolamento
        - Tem seus proprios recursos do sistema
        - Comunicação entre processos (IPC) é complexa e robusta (Mais dificil)
        - A falha de um processo não afeta os outros
        - Custo alto de criação
        - Isolar aplicações

        * Thread:
        - Unidade de execução dentro do processo
        - Compartilham o espaço de mémoria umas com as outras
            - Sem isolamento
        - Comunicação rápida e simples (Mais facil)
        - A falha de uma thread pode afetar todo o processo
        - Custo baixo de criação
        - Realizar operações concomitantes

## Aula 2
- Sistemas Distribuidos x Sistemas Paralelos
- SD:
    - GRID
    - Fracamente Acoplados
    - Heterogêneo
    - Latência
    - Programação (Multitarefa) Concomitante / Concorrente
        - Compartilha Recursos
            - CPU
            - RAM
            - GPU
            - Memória Secundária
- SP:
    - Cluster
    - Fortemente Acoplados (Rack)
    - Homogêneo
    - GPU e Multicores
    - Programação (Multitarefa) Paralela = CUDA
        - 'n' processos executando ao mesmo tempo 't'
- Ambos:
    - Modelo TCP/IP
        - IP
        - Dominio
        - Porta
        - Socket
        - UDP ou TCP

- Porquê usar Sistemas Distribuídos?
    - Compartilhar recursos
        - 'Dividir para conquistar'

- Como Sistemas Distribuídos operam?
    - Através da Comunicação
        - Comunicação de Dados
            - Bytes
            - Protocolo TCP/IP (Dita as regras da comunicação)
                - Lexemas (Simbolos)
                - Sintaxes
                - Semântica
                - Serialização (Define como será enviado o dado)
            - Categorias
                - Broadcast
                - Multicast
                - Unicast
                - Halfduplex
                    - Bloqueante (Comunicação unilateral)
                - Fullduplex
            - Escrever = Writer = Sender
            - Ler = Reader = Receiver

- Arquitetura
    - Cliente-Servidor
        - Server Side
            - ClientSocket
            - Ip, Mask
            - Reader
            - Writer
        - Client Side
            - ServerSocket
            - Ip, Mask
            - Reader
            - Writer

- Thread
    - Mini processo dentro de um processo
    - Somente existe em Sistemas Distribuídos, não existem em Sistemas Paralelos
    - "Envolvem" rotinas ou tarefas ou instruções e executa-las concorrentemente
        - Encapsulam
        - Wrapping
    - Tipos
        - Com seção crítica = Memória Compartilhada
            - Sincronismo 
                - Sistemas Operacional (sincronized)
                - Semaphore
                - Lock
        - Sem seção crítica = sem Memória Compartilhada
        - Seção Crítica
            - Barramento
            - Impressora
            - Ram / Memória

- Processo
    - Classe Thread -> Sem memória crítica
    - Interface Runnable -> Com memória crítica
        - Objeto-Thread
            - Responsavél por envolver/circundar uma rotina
            - MinhaThread t1 = new MinhaThread(); (Solução A)

## Aula 3
- Gestor Firewall
    - Adicionar Nova Regra
        - Porta: 12345
        - Protocolo: TCP ou UDP ou Ambas

## Aula 4
- Sistemas Distribuidos
    - Foco na comunicação
        - Bloqueante
            - Recurso
                - Seção Critica
                    - Recurso
                    - Memória
            - Thread
                - Programação Concorrente
                - Com Memória Compartilhada
                - Sem Memória Compartilhada
- Exercicio
    - Venda de rifas
        - Sem Saldo Compartilhado
        - Com Saldo Compartilhado
        