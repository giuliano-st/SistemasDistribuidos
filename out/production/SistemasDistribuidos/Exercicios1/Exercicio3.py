#Fazer um código orientado a objetos (Java, C# ou Python) que:
# a) leia o arquivo numeros.txt (com 10 números, um abaixo do outro) e popule seus números numa lista de inteiros
# b) leia o arquivo nomes.txt (com 15 nomes, um abaixo do outro) e popula seus nomes numa lista de string
# c) exiba as listas respectivas

#Observação:
# - Implemente threads para as operações de ler/popular arquivo (fique atento se não seria nécessario 2 métodos)
# - Implemente threads para as operações de exibir listas

import threading

listaInt = []
listaNomes = []

def ler_numeros():
    with open("numeros.txt", "r") as arquivo:
        content = arquivo.read()

    for i in content.splitlines():
        listaInt.append(int(i))

    arquivo.close()

def exibir_numeros():
    for i in listaInt:
        print(i)

def ler_nomes():
    with open("nomes.txt", "r") as arquivo:
        content = arquivo.read()

    for i in content.splitlines():
        listaNomes.append(i)

    arquivo.close()

def exibir_nomes():
    for i in listaNomes:
        print(i)

t1 = threading.Thread(target=ler_numeros)
t2 = threading.Thread(target=ler_nomes)
t3 = threading.Thread(target=exibir_numeros)
t4 = threading.Thread(target=exibir_nomes)

t1.start()
t2.start()
t3.start()
t4.start()