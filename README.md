Per eseguire il progetto basta utilizzare un IDE come Intellij IDEA e eseguire il run 
della classe main.

Attraverso questo poc è possibile osservare il funzionamento di:

- Comunicazione tra Camel Route;
- Utilizzo di Processor;
- Utilizzo di Aggregatori;
- Binding dei dati (marshal e unmarshal);

Il flusso che è dietro all'utilizzo di questo progetto è quello di:

- Leggere da un file.cvs;
- Processare il file.csv aggiungendo delle colonne ad hoc;
- Filtrare il nuovo file csv con le colonne aggiuntive e indirizzare il traffico verso rotte differenti;
- Utilizzare solo il file csv (con la decitura fila true per interrogare un endpoint di JSONPlaceholder)