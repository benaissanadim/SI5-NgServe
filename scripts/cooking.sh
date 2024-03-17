#!/bin/bash

# Vérifie si l'argument a été passé
if [ $# -ne 1 ]; then
  echo "Usage: $0 <nombre>"
  exit 1
fi

# Récupère le nombre passé en argument
nombre=$1

# Construit l'URL avec le nombre
url="http://localhost:4000/preparations/cooking/$nombre"

# Effectue la requête Curl
curl "$url"
