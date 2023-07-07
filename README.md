# UDP Client-Server

## UDPClient

### Description
The `UDPClient` class represents a client application that communicates with a server using UDP socket programming principles. It prompts the user for the server IP address, gremlin probability, and an HTTP GET request. The client sends the request to the server, receives packets of data, and checks for any packet corruption using a checksum. It displays the received data and identifies any corrupted packets.

### Class Methods

#### `main(String[] args) throws Exception`
- Entry point of the UDPClient application.
- Establishes a connection with the server using a UDP socket.
- Prompts the user for server IP address, gremlin probability, and HTTP GET request.
- Sends the request to the server and receives packets of data.
- Checks for packet corruption and displays the received data.
- Closes the client socket.

#### `Gremlin(double p, byte[] pack) : byte[]`
- Applies the gremlin function to a packet by introducing random byte corruptions based on the given probability.
- Returns the modified packet.

#### `calcChecksum(byte[] data) : int`
- Calculates the checksum of a byte array.
- Returns the checksum value.

## UDPServer

### Description
The `UDPServer` class represents a server application that communicates with clients using UDP socket programming principles. It listens for incoming requests, retrieves requested files, and sends the data back to the clients as packets. It handles file not found errors and generates appropriate HTTP headers.

### Class Methods

#### `main(String[] args) throws Exception`
- Entry point of the UDPServer application.
- Creates a DatagramSocket to listen for incoming packets on port 10005.
- Receives packets from clients, retrieves requested files, and generates appropriate HTTP headers.
- Sends data packets to the clients in chunks.
- Sends a null byte packet to signal the end of transmission.
- Exits the program.

## Usage
1. Compile and run the `UDPClient` and `UDPServer` classes separately.
2. Enter the server IP address, gremlin probability, and HTTP GET request in the client application.
3. The client will send the request to the server, receive data packets, and display the received data.
4. The server will listen for requests, retrieve requested files, and send data packets back to the client.
5. The client will display any corrupted packets and indicate the successful reception of all packets.

