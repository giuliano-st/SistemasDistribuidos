#Divisão e Conquista: Soma de Sublistas
#Contexto: O processamento de grandes volumes de dados numéricos.

#Problema: Dado um vetor ou lista com 10.000 números inteiros aleatórios, divida essa lista em 4 partes iguais.

#Ação: Crie 4 threads. Cada thread recebe apenas uma das partes como parâmetro de entrada, calcula a soma dos elementos dessa sublista e retorna o valor final.

#Encerramento: A thread principal aguarda o fim das 4 threads, coleta as 4 somas parciais e calcula a soma total.

import threading
import random

lista1 = []
lista2 = []
lista3 = []
lista4 = []

listaR = []
for i in range(10000):
    listaR.append(random.randint(1, 1000))

def separa_lista(lista, ini, fim):
    for i in range(ini, fim):
        lista.append(listaR[i])

t1 = threading.Thread(target=separa_lista, args=(lista1, 0, 2499))
t2 = threading.Thread(target=separa_lista, args=(lista2, 2500, 4999))
t3 = threading.Thread(target=separa_lista, args=(lista3, 5000, 7499))
t4 = threading.Thread(target=separa_lista, args=(lista3, 7500, 9999))

t1.start()
t2.start()
t3.start()
t4.start()

print(lista1)
print("------")
print(lista2)
print("------")
print(lista3)
print("------")
print(lista4)
print("------")
print(listaR)