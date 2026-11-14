# Útil para testar SQL
recreate_db:
	sudo docker exec -it postgres-container psql -U postgres -c "DROP DATABASE IF EXISTS bicicletas;"
	sudo docker exec -it postgres-container psql -U postgres -c "DROP USER IF EXISTS biker;"
	sudo docker exec -it postgres-container psql -U postgres -c "CREATE USER biker WITH PASSWORD 'bikemaster';"
	sudo docker exec -it postgres-container psql -U postgres -c "CREATE DATABASE bicicletas OWNER biker;"
	sudo docker cp create-table.sql postgres-container:/home/create-table.sql
	sudo docker exec -it postgres-container psql -U biker -h localhost bicicletas -f /home/create-table.sql
	sudo docker exec -it postgres-container psql bicicletas -U biker -c "INSERT INTO estados VALUES (0, 'disponível'), (1, 'alugada'), (2, 'em manutenção');"
	sudo docker exec -it postgres-container psql -U biker -h localhost bicicletas -c "\dt"

list_tables:
	sudo -u postgres psql bicicletas -c "\d bicicletas"
	sudo -u postgres psql bicicletas -c "\d estados"
	sudo -u postgres psql bicicletas -c "\d historico_estado"
	sudo -u postgres psql bicicletas -c "\d historico_operacao"
	sudo -u postgres psql bicicletas -c "\d utilizadores"

set_up_db:
	sudo su - postgres
	createuser --no-superuser --no-createdb --no-createrole user1
	createdb -O user1 bd1
	psql bd1
	psql bd1 -U user1 -h localhost

# Makefile for Java project

# Variables
SRC_DIR = src
BUILD_DIR = build/classes
LIB = resources/postgresql.jar
CLASSPATH = $(BUILD_DIR):$(LIB)
MAIN = bike.Servidor
CLIENTE = bike.ClienteGeral
JAVA_FILES = $(shell find $(SRC_DIR) -name "*.java") #$(SRC_DIR)/**/*.java

# Targets
all: compile run

compile:
	javac -d $(BUILD_DIR) -classpath $(CLASSPATH) $(JAVA_FILES)

run:
	java -classpath $(CLASSPATH) $(MAIN)

clean:
	rm -rf $(BUILD_DIR)/*

run_cliente:
	java -classpath $(CLASSPATH) $(CLIENTE)

# Usage:
# To compile: make
# To run: make run
# To clean: make clean
