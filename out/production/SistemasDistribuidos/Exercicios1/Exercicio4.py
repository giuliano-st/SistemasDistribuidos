#Fazer um código orientado a objetos (Java, C# ou Python) que:
# a) leia o arquivo numeros1.txt (com 10 números, um abaixo do outro) e popule seus números numa lista de inteiros
# b) leia o arquivo numeros2.txt (com 10 números, um abaixo do outro) e popule seus números na lista de inteiros anterior

#Observação:
# - Implemente threads para as operações de ler/popular
# - Com memória compartilhada

import threading
import time

listaInt = []
class ListaDeNumeros:
    def __init__(self):
        self.listaInt = []
        self.lock = threading.Lock()
        
    def ler_numeros(self):
        with self.lock:
            with open("numeros1.txt", "r") as arquivo1:
                for linha in arquivo1:
                    numero = int(linha.strip())
                    self.listaInt.append(numero)

    def ler_numeros2(self):
        with self.lock:
            with open("numeros2.txt", "r") as arquivo2:
                for linha in arquivo2:
                    numero = int(linha.strip())
                    self.listaInt.append(numero)
                    
    def exibir_numeros(self):
        with self.lock:
            for i in self.listaInt:
                print(i)
            #print(self.listaInt)

if __name__ == "__main__":
    lista_numeros = ListaDeNumeros()

    t1 = threading.Thread(target=lista_numeros.ler_numeros)
    t2 = threading.Thread(target=lista_numeros.ler_numeros2)

    t1.start()
    t2.start()

    t1.join()
    t2.join()

    lista_numeros.exibir_numeros()