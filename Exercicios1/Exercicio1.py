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

listaR = [random.randint(1, 1000) for _ in range(10000)]

lista_somas = []

def separa_lista(lista_final, ini, fim, lista_inicial):
    for i in range(ini, fim + 1):
        lista_final.append(lista_inicial[i])

        soma = sum(lista_final)
        lista_somas.append(soma)

t1 = threading.Thread(target=separa_lista, args=(lista1, 0, 2499, listaR))
t2 = threading.Thread(target=separa_lista, args=(lista2, 2500, 4999, listaR))
t3 = threading.Thread(target=separa_lista, args=(lista3, 5000, 7499, listaR))
t4 = threading.Thread(target=separa_lista, args=(lista4, 7500, 9999, listaR))

t1.start()
t2.start()
t3.start()
t4.start()

t1.join()
t2.join()
t3.join()
t4.join()

soma_final = sum(lista_somas)

print(lista1)
print("------")
print(lista2)
print("------")
print(lista3)
print("------")
print(lista4)
print("------")
print(listaR)

print(f"Soma das listas: {soma_final}")