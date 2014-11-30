printf "Veuillez entrer votre nom et votre prenom: "

read identity
set ${identity:-" "}

if [[ $# -eq 2 ]]; then
    echo "Bienvenue $2 $1"
    exit 0
else
    exit 1
fi
