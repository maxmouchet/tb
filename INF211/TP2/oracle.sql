-- Afficher les prescriptions qui contiennent du doliprane
SELECT * FROM prescription WHERE prescription.medicament LIKE 'doliprane%';

-- Afficher les prescriptions de ventoline ordonées par durée de traitement décroissante
SELECT * FROM prescription WHERE prescription.medicament = 'ventoline'
ORDER BY prescription.duree DESC;

-- Afficher les prescriptions de ventoline ayant une durée supérieure à 90 jours
SELECT * FROM prescription WHERE prescription.medicament = 'ventoline'
AND prescription.duree > 90;

-- Afficher les prescriptions de ventoline ayant une durée supérieure à 90 jours
-- ou une posologie supérieure à 6 prises / jour
SELECT * FROM prescription WHERE prescription.medicament = 'ventoline'
AND (prescription.duree > 90 OR prescription.posologie > 6);

-- Afficher pour chaque prescription le prix de la visite qui lui est associée
SELECT DISTINCT prescription_id, prix FROM prescription NATURAL JOIN visite;

-- Afficher les noms des patients et les identifiants des médécins
-- qu'ils ont visités le 15 janvier 2012
SELECT visite.date_visite,
       visite.medecin AS id_medecin,
       patient.nom    AS patient
FROM visite JOIN patient ON patient.patient_id = visite.patient
WHERE visite.date_visite = '15/01/12';

-- Afficher pour chaque ayant-droit les SSN, nom et prénom de l'assuré auquel
-- il est rattaché
SELECT patient.nom     AS ayant_droit,
       patient.numsecu AS ssn,
       p1.numsecu      AS ssn_assure,
       p1.nom          AS nom_assure,
       p1.prenom       AS prenom_assure
FROM patient p1 JOIN patient on patient.rattachement = p1.patient_id;

-- Afficher les noms des patients et de leur médecin référent
SELECT patient.nom AS patient,
       medecin.nom AS medecin
FROM patient JOIN medecin on medecin.medecin_id = patient.medecin_referent;

-- Afficher les noms des patients et de leur médecin référent si présent
SELECT patient.nom AS patient,
       medecin.nom AS medecin
FROM patient LEFT JOIN medecin on medecin.medecin_id = patient.medecin_referent;

-- Afficher les noms des patients qui ont visité leur médecin référent
-- le 15 janvier 2012
SELECT patient.nom, visite.date_visite
FROM patient JOIN visite ON visite.patient = patient.patient_id
WHERE visite.medecin = patient.medecin_referent AND visite.date_visite = '15/01/12';

-- Afficher les médecins qui ne sont pas généralistes
SELECT * FROM medecin WHERE medecin.specialite != 'generaliste';

-- Afficher les patients qui n'ont pas de médecin référent
SELECT * FROM patient WHERE patient.medecin_referent IS NULL;

-- Afficher les patients qui n'ont jamais vu de médecin
SELECT patient.* FROM patient
WHERE patient.patient_id NOT IN (SELECT visite.patient FROM visite);

-- Afficher les prescriptions qui ne contiennent pas de doliprane
SELECT * FROM prescription
WHERE prescription_id NOT IN
    (SELECT prescription_id FROM prescription WHERE medicament LIKE 'doliprane%');

-- Afficher les noms des patients qui sont ressortis d'une visite sans prescription
SELECT DISTINCT patient.nom, date_visite FROM visite
NATURAL LEFT JOIN prescription
JOIN patient ON patient.patient_id = patient
WHERE prescription.prescription_id IS NULL;

-- Afficher le nombre total de médecins
SELECT COUNT(*) FROM medecin;

-- Afficher le nombre total de médecins par spécialité
SELECT medecin.specialite, COUNT(*) FROM medecin GROUP BY medecin.specialite;

-- Afficher les personnes qui ont un ayant droit
SELECT DISTINCT p1.patient_id, p1.nom
FROM patient p1 JOIN patient ON patient.rattachement = p1.patient_id;

-- Afficher les personnes qui ont plus de trois ayants droit
SELECT p1.patient_id, p1.nom FROM patient p1
JOIN patient ON patient.rattachement = p1.patient_id
GROUP BY p1.patient_id, p1.nom HAVING COUNT(*) > 3;

-- Afficher les personnes qui ont exactement un ayant droit
SELECT p1.patient_id, p1.nom FROM patient p1
JOIN patient ON patient.rattachement = p1.patient_id
GROUP BY p1.patient_id, p1.nom HAVING COUNT(*) = 1;

-- Afficher les patients qui ont vu un généraliste et un spécialiste le même jour
SELECT patient.nom FROM visite
JOIN patient ON patient.patient_id = visite.patient
JOIN medecin ON medecin.medecin_id = visite.medecin
GROUP BY patient.nom HAVING COUNT(DISTINCT medecin.specialite) > 1;

-- Afficher les visites où a été prescrit doliprane ou aspegic
SELECT date_visite FROM prescription
NATURAL JOIN visite
WHERE (medicament LIKE 'doliprane%' OR medicament = 'aspegic')
GROUP BY date_visite;

-- Afficher les visites où a été prescrit doliprane et aspegic
SELECT date_visite FROM prescription
NATURAL JOIN visite
WHERE medicament LIKE 'doliprane%' OR medicament = 'aspegic'
GROUP BY date_visite HAVING COUNT(DISTINCT medicament) > 1;

-- Afficher les noms des patients qui sont ressortis d'une seule
-- et unique visite sans prescription
SELECT DISTINCT patient.nom FROM visite
NATURAL LEFT JOIN prescription
JOIN patient ON patient.patient_id = patient
WHERE prescription.prescription_id IS NULL
GROUP BY patient.nom HAVING COUNT(patient.nom) = 1;

-- Afficher les patients qui ont vu un autre médecin que leur médecin référent
-- plus de trois fois en janvier 2012
SELECT patient.nom
FROM patient JOIN visite ON visite.patient = patient.patient_id
WHERE visite.medecin != patient.medecin_referent AND visite.date_visite LIKE '%/01/12'
GROUP BY patient.nom HAVING COUNT(patient.nom) > 3;

-- Afficher les patients qui n'ont jamais vu leur médecin référent
SELECT patient.nom FROM patient WHERE patient.patient_id NOT IN
    (SELECT patient.patient_id
     FROM patient JOIN visite ON visite.patient = patient.patient_id
     WHERE visite.medecin = patient.medecin_referent);

-- Afficher les patients qui ont vu des médecins de toutes les spécialités