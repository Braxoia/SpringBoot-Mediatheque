/**
 * Global storage for the application
 * @type {{
 *  bearerToken: null,
 *  submittedLogin: null | {username: string, password: string},
 *  userData: null | {id: number, bibliothecaire: boolean, password: string, username: string, additionalData: Object},
 * }}
 */
const storage = {
  bearerToken: null,
  submittedLogin: null,
  /**
   * @type {null | {id: number, bibliothecaire: boolean, password: string, username: string, additionalData: Object}}
   */
  userData: null,
};

/**
 * Entrypoint
 */
document.addEventListener('DOMContentLoaded', function () {
  bindLinkToAnchor();

  document.getElementById("formConnection").addEventListener("submit", connect);
  document.getElementById("formInscription").addEventListener("submit", register);
  document.getElementById("formAjoutDoc").addEventListener("submit", ajoutDoc);
});


/**
 * @param {SubmitEvent} e l'event du formulaire de connection
 */
async function connect(e) {
  e.preventDefault();
  let response, token;
  try {
    response = await sendForm(e);
    token = await response.text();
  } catch (error) {
    printError(error);
    return;
  }
  switch (response.status) {
    case 200:
      storage.bearerToken = token;
      await initDashboard();
      break;
    case 401:
      printError("Mot de passe incorrect");
      break;
    case 404:
      printError("Nom d'utilisateur indisponible");
      break;
    default:
      printError("Erreur inconnue");
  }
}
/**
 * @param {SubmitEvent} e l'event du formulaire de connection
 */
async function register(e) {
  e.preventDefault();
  let response;
  try {
    response = await sendForm(e);
  } catch (error) {
    printError(error);
    return;
  }
  switch (response.status) {
    case 201:
      alert("Utilisateur créé");
      moveTo("#signin");
      break;
    case 412:
      printError("Nom d'utilisateur indisponible");
      break;
    default:
      printError("Erreur inconnue");
  }
}

/**
 * Supprime un compte d'utilisateur
 * @param {number | undefined} id l'identifiant du compte
 */
async function supprimerCompte(id) {
  if (!id) {
    id = storage.userData.id;
  }
  let response;
  try {
    response = await fetch(`/api/utilisateurs/delete/${id}`, {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${storage.bearerToken}`,
      },
    });
  } catch (error) {
    printError(error);
    return;
  }
  switch (response.status) {
    case 200:
      if (id === storage.userData.id) {
        alert("Votre compte a été supprimé");
        disconnect();
      } else {
        alert("Utilisateur supprimé");
        updateAccountList();
      }
      break;
    default:
      printError("Erreur inconnue");
  }
}

/**
 * @param {SubmitEvent} e l'event du formulaire d'ajout de document
 */
async function ajoutDoc(e) {
  e.preventDefault();
  let response, text;
  try {
    response = await sendForm(e);
    text = await response.text();
  } catch (error) {
    printError(error);
    return;
  }
  switch (response.status) {
    case 201:
      await updateDocumentList();
      break;
    case 417:
      printError("Expectation failed: " + text);
      break;
    default:
      printError("Erreur inconnue");
  }
}

/**
 * Tente d'emprunter le doc pour l'utilisateur actuel (/api/documents/emprunter/{id})
 * @param id {number} le numéro du document à emprunter
 */
async function emprunt(id) {
  let response, text;
  try {
    response = await fetch(`/api/documents/emprunter/${id}`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${storage.bearerToken}`
      }
    });
    text = await response.text();
  } catch (error) {
    printError(error);
    return;
  }
  switch (response.status) {
    case 200:
      alert("Emprunt effectué");
      await updateDocumentList();
      break;
    case 400:
      printError("Le document est déjà emprunté par quelqu'un d'autre");
      break;
    case 404:
      printError("Le document n'existe pas");
      break;
    case 417:
      printError("Expectation failed: " + text);
      break;
    default:
      printError("Erreur inconnue");
  }
}

/**
 * Tente de rendre le doc pour l'utilisateur actuel (/api/documents/emprunter/{id})
 * @param id {number} le numéro du document à retourner
 */
async function retour(id) {
  let response;
  try {
    response = await fetch(`/api/documents/retourner/${id}`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${storage.bearerToken}`
      }
    });
  } catch (error) {
    printError(error);
    return;
  }
  switch (response.status) {
    case 200:
      alert("Retour effectué");
      await updateDocumentList();
      break;
    case 400:
      printError("Le document est dispo, inutile de le retourner");
      break;
    case 404:
      printError("Le document n'existe pas");
      break;
    default:
      printError("Erreur inconnue");
  }
}


async function initDashboard() {
  let response = await fetch(`/api/utilisateurs/${storage.submittedLogin.username}`, {
    method: "GET",
    headers: { "Authorization": "Bearer " + storage.bearerToken },
  });
  storage.userData = await response.json();
  document.body.dataset.accountType=storage.userData.type;
  await updateDocumentList();
  moveTo("#dashboard");
}

async function updateDocumentList() {
  let response = await fetch(`/api/documents`, {
    method: "GET",
    headers: { "Authorization": "Bearer " + storage.bearerToken },
  });
  let documents = await response.json();
  let list = document.getElementById("documentList");
  list.innerHTML = "";
  console.log(documents);
  for (let e of documents) {
    let li = document.createElement("li");
    if (! storage.userData.bibliothecaire) {
      if (e.emprunteur == "null") {
        li.classList.add("empruntable");
      } else if (e.emprunteur == storage.userData.username) {
        li.classList.add("retourable");
      } else {
        li.classList.add("indispo");
      }
    }
    li.innerHTML = `${e.id} ${e.titre}  <button onclick="emprunt(${e.id})">Emprunter</button> <button onclick="retour(${e.id})">Retourner</button>`;
    list.appendChild(li);
  }
}

async function updateAccountList() {
  let response = await fetch(`/api/utilisateurs`, {
    method: "GET",
    headers: { "Authorization": "Bearer " + storage.bearerToken },
  });
  let utilisateurs = await response.json();
  let list = document.getElementById("accountList");
  list.innerHTML = "";
  for (let e of utilisateurs) {
    let li = document.createElement("li");
    li.innerHTML = `${e.id} ${e.username} ${e.type} <button onclick="supprimerCompte(${e.id})">Supprimer</button>`;
    list.appendChild(li);
  }
}

function disconnect() {
  for (const i in storage) {
    storage[i] = null;
  }
  moveTo("#signin");
  alert("Déconnection effectuée avec succès");
}

/**
 * Change la vue sur la page courante
 * @param {String} anchor identifiant de la section à afficher (ex : #section1)
 */
function moveTo(anchor) {
  document.querySelector(".active").classList.remove("active");
  if (anchor.includes("accounts")) updateAccountList().then();
  document.querySelector(anchor).classList.add("active");
}

/**
 * Mets à jour les liens du dom ayant la classe moveTo pour qu'ils puissent naviguer entre les sections.
 */
function bindLinkToAnchor() {
  for (const liens of document.querySelectorAll('a.moveTo')) {
    liens.addEventListener('click', function (e) {
      e.preventDefault();
      const hash = this.getAttribute('href');
      moveTo(hash);
    });
  }
}

/**
 * Affiche une erreur
 * @param {String} message
 */
function printError(message) {
  alert(message);
  console.error(message);
}



/**
 * @param {SubmitEvent} event le formulaire attaché
 * @return {Promise<Response>} le résultat du fetch
 */
async function sendForm(event) {
  const form = event.currentTarget;
  const url = form.action;
  const formData = new FormData(form);
  storage.submittedLogin = Object.fromEntries(formData.entries());
  const fetchOptions = {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(storage.submittedLogin),
  };
  if (storage.bearerToken) {
    fetchOptions.headers.Authorization = `Bearer ${storage.bearerToken}`;
  }
  return await fetch(url, fetchOptions);
}
