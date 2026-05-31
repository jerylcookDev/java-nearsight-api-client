# Nearsight MCP Server

This project requires a running [Ollama](https://ollama.ai) instance with the **llama3** model installed.

We use  @Tool annotation in Spring AI to allow NearSight MCP server to register functions as agent discoverable tools — for example, so an agent framework (LangChain4j, Spring AI agent routing, or MCP clients). Tehse tools can automatically discover and call your endpoints.

---

## 🚀 Prerequisites

- Docker installed and running
- ~4GB free disk space for the `llama3` model
- install node.sh for working with claude

npm install -g @anthropic-ai/mcpb
mcpb init
mcpb pack

---

## 🐳 Run Ollama in Docker

Start Ollama as a background container:

```bash
docker run -d  --name ollama  -p 11434:11434   -v ollama:/root/.ollama ollama/ollama --swap


   curl -X POST http://localhost:11434/api/generate \
     -d '{
           "model": "llama3",
           "prompt": "Why is the sky blue?"
         }'


```
## 🔧 Environment Settings
  
The MCP server requires the following environment variables:

| Variable            | Description                                   | Example Value            |
|---------------------|-----------------------------------------------|--------------------------|
| `OLLAMA_BASE_URL`   | Base URL of the Ollama server                 | `http://localhost:11434` |
| `OLLAMA_CHAT_MODEL` | Default Ollama model for chat/inference       | `llama3`                 |
| `MCP_AUTH_KEY`      | Authentication key for securing MCP endpoints | `your-secret-key`        |
### Setting Environment Variables

On **Linux / macOS**:

```bash
export OLLAMA_BASE_URL=http://localhost:11434
export OLLAMA_CHAT_MODEL=llama3
export MCP_AUTH_KEY=your-secret-key
```


 
### First steps
Start mongoDB

```
docker run -d \
  --name my-mongo \
  -p 27017:27017 \
  mongo:7.0
```

Get ollama

```
docker run -d  --name ollama --gpus all -it  -p 11434:11434   -v ollama:/root/.ollama ollama/ollama

#this below worked..
docker run --gpus all -it \
  -p 11434:11434 \
  ollama/ollama

```

```
docker start ollama

docker exec -it ollama bash

   ollama pull llama3.1
   ollama run llama4
   
   ollama run llama4 -p "Explain quantum entanglement simply."
   
   talk to chat directly
   ollama run llama3.1
```
- nvidia-smi prints GPU status.
docker run --rm --gpus all nvidia/cuda:13.0 nvidia-smi


 /c/users/twoen/AppData/Roaming/Claude/logs
 