import threading

def tarefa1(lista):
    for i in range(900):
        print("Thread 1 executando!")

def tarefa2():
    for i in range(900):
        print("Thread 2 em ação!")

lista1 = [6, 4, 3, 1, 3, 4, 5]
t1 = threading.Thread(target=tarefa1,args=lista1)
t1.start

t2 = threading.Thread(target=tarefa2)
t2.start
#Bolha, Quick, Seleção