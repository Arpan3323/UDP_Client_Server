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

#### `main(String[] args) throws Exception`
- **Description**: The `main` method is the entry point of the UDPServer application.
- **Functionality**:
  - Creates a DatagramSocket to listen for incoming packets on port 10005.
  - Sets up an `ExecutorService` to handle multiple client requests concurrently.
  - Loops indefinitely to receive packets from clients and launch a new `ClientHandler` for each client request.
- **Parameters**:
  - `String[] args`: Command-line arguments (not used).
- **Throws**:
  - `Exception` if any error occurs during execution.

### Nested Class `ClientHandler`

The `ClientHandler` class represents a worker thread responsible for handling client requests.

#### Constructor `ClientHandler(DatagramPacket receivePacket, DatagramSocket serverSocket)`

- **Description**: The constructor initializes a `ClientHandler` instance with the necessary parameters.
- **Parameters**:
  - `DatagramPacket receivePacket`: The packet received from the client.
  - `DatagramSocket serverSocket`: The server socket for sending responses to the client.

#### Method `run()`

- **Description**: The `run` method is called when the `ClientHandler` thread is started.
- **Functionality**:
  - Retrieves the client's IP address, port, and the request data from the `receivePacket`.
  - Extracts the requested file name from the request.
  - Attempts to read the requested file and handle file not found errors.
  - Constructs an HTTP header with appropriate status code and content length.
  - Sends the header to the client as packets.
  - Sends the file content to the client as packets.
  - Sends a null byte packet to signal the end of transmission.
- **No Parameters**.
- **No Return Value**.

## Usage
1. Compile and run the `UDPClient` and `UDPServer` classes separately.
2. Enter the server IP address, gremlin probability, and HTTP GET request in the client application.
3. The client will send the request to the server, receive data packets, and display the received data.
4. The server will listen for requests, retrieve requested files, and send data packets back to the client.
5. The client will display any corrupted packets and indicate the successful reception of all packets.

