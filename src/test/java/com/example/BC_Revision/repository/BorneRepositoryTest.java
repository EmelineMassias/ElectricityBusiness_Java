package com.example.BC_Revision.repository;

import com.example.BC_Revision.model.Borne;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BorneRepositoryTest {

    @Autowired
    BorneRepository borneRepository;


    @Test
    void testSave() {
        //Vérifie si la sauvegarde se fait correctement
        //On donne un nom à la borne
        String nom = "Borne 1";

        //On créé une borne "borne 1"
        Borne borne1 = new Borne();
        //On lui donne le nom borne 1
        borne1.setNomBorne(nom);

        //On créé la borne 2
        Borne borne2 = new Borne();
        //On lui donne le nom borne 1
        borne2.setNomBorne(nom);
        //et on sauvegarde dans le repo
        Borne borneEnregistree = borneRepository.save(borne2);

        // On vérifie que le nom de la borne enregistrée (borne 2) est égale au nom utilisé pour instancier la borne (borne 1)
        assertEquals(nom, borneEnregistree.getNomBorne());
        //On vérifie que le nom de la borne enregistrée (borne 2) est égale au nom de la borne créé précédemment (borne 1)
        assertThat(borneEnregistree.getNomBorne()).isEqualTo(borne1.getNomBorne());
        //On vérifie que l'id de la borne enregistrée est positif (et non null)
        assertThat(borneEnregistree.getId()).isPositive();
    }

    @Test
    void testGet() {
        //On test le findById du repository fonctionne
        //on instancie un nom borne 1
        String nom = "Borne 1";

        //On créé la borne
        Borne borne1 = new Borne();
        //On set le nom
        borne1.setNomBorne(nom);
        //On sauvegarde dans le repo
        Borne borneSauvegardee = borneRepository.save(borne1);
        //On créé "borne recup" qui est l'objet findById du repository
        Borne borneRecup = borneRepository.findById(borneSauvegardee.getId()).get();
        //On vérifie que le nom de la borne récupéré est égale au nom utilisé pour instancier la borne 1
        assertEquals(nom, borneRecup.getNomBorne());
        //On vérifie si le nom de la borneRecup est égal au nom de la borneSauvegardée
        assertThat(borneRecup.getNomBorne()).isEqualTo(borneSauvegardee.getNomBorne());
        //On vérifie que l'id de la borne enregistree est positif
        assertThat(borneRecup.getId()).isPositive();

    }

}