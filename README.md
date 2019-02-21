# Application Pokemon
Voici une application de type liste-détail Pokémon. L'application fonctionne aussi bien en mode portrait qu'en mode paysage. Le mode hors-ligne n'est en revanche pas disponible.

# API

L'API utilisée est l'API  [PokéAPI](https://pokeapi.co/). Pour l'utilisation de celle-ci, je me suis permis d'utiliser la [librairie](https://github.com/PokeAPI/pokekotlin) que l'on peut retrouver sur le site officiel de l'API.

## 	Liste des pokémons

Le premier écran sur lequel nous arrivons au lancement de l'application est la liste des pokémons. Chaque élément compte le numéro du Pokémon ainsi que son nom.
Sur le bas de l'écran, nous retrouvons une barre de navigation permettant de naviguer d'une page de la liste à une autre.

## Écran des détails

L'API comportant un très grand nombre de champs, j'ai choisi d'implémenter les attributs du pokémon suivants :
- id
- nom
- poids
- taille
- types
- techniques
- image principale
