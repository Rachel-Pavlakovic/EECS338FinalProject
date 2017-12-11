all: AutoDealer, BJClient, Server, Deck, Hand

AutoDealer:
	javac AutoDealer.java

BJClient:
	javac BJClient.java

Server:
	javac Server.java

Deck:
	javac Deck.java

Hand:
	javac Hand.java