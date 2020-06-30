/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appjee.processing.logic;

import com.appjee.processing.dao.DAO;
import com.appjee.receptionfacade.domain.SoapMessage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author myal6
 */
public class DecipherTester {

    private SoapMessage soapMessage;
    private DAO dao;

    public DecipherTester(SoapMessage soapMessage) throws ClassNotFoundException, SQLException {
        this.soapMessage = soapMessage;
        dao = new DAO();
        dao.connectionToDb();
        this.soapMessage.setInfo("La dépression, également appelée dépression caractérisée, dépression clinique ou dépression majeure, est un trouble mental caractérisé par des épisodes de baisse d'humeur (tristesse) accompagnée d'une faible estime de soi, d’une perte de mémoire, d’une perte ou prise de poids plus ou moins importante ainsi que d'une perte de plaisir ou d'intérêt dans des activités habituellement ressenties comme agréables par l'individu. Cet ensemble de symptômes (syndrome individualisé et anciennement classifié dans le groupe des troubles de l'humeur par le manuel diagnostique de l'association américaine de psychiatrie) figure depuis la sortie du DSM-5 en mai 2013 dans la catégorie appelée « troubles dépressifs ». Le terme de « dépression » est cependant ambigu ; il est en effet parfois utilisé dans le langage courant pour décrire d'autres troubles de l'humeur ou d'autres types de baisse d'humeur moins significatifs qui ne sont pas des dépressions proprement dites.\n"
                + "\n"
                + "La dépression est une condition handicapante qui peut retentir sur le sommeil, l'alimentation et la santé en général avec notamment un risque de suicide dans les cas les plus graves (surtout dans la dépression mélancolique), ainsi que sur la famille, la scolarité ou le travail. Aux États-Unis, approximativement 3,4 % des individus dépressifs meurent par suicide et plus de 60 % des individus qui se sont suicidés souffraient de dépression ou d'un autre trouble de l'humeur1. Les individus souffrant de dépression ont une espérance de vie raccourcie par rapport aux autres individus, en partie à cause d'une plus grande susceptibilité à d'autres maladies et au risque de suicide. Les patients actuellement ou anciennement dépressifs sont parfois stigmatisés.\n"
                + "\n"
                + "Le diagnostic de la dépression se base sur plusieurs éléments : le ressenti personnel rapporté par le patient, le comportement perçu par son entourage et le résultat d'un examen psychologique. Les médecins peuvent prescrire des examens complémentaires pour rechercher d'autres maladies qui peuvent causer des symptômes similaires. La maladie est plus fréquente entre 20 et 30 ans, avec un pic plus tardif entre 30 et 40 ans2.\n"
                + "\n"
                + " \"Je suis secret\" "
                + "Les patients sont habituellement traités avec un médicament antidépresseur, et dans certains cas suivent une psychothérapie. L'hospitalisation peut s'avérer nécessaire dans le cas d'auto-négligence, s'il existe un risque significatif de suicide ou pour la sécurité de l'entourage. Les dépressions résistantes aux traitements médicamenteux et à la psychothérapie peuvent être traitées par électroconvulsivothérapie ou par stimulation magnétique transcrânienne. La durée de la dépression est grandement variable, pouvant aller d'un épisode unique de quelques semaines à une longue période d'épisodes dépressifs prolongés et répétés (dans ce cas, il s'agit de dépression récurrente ou trouble unipolaire, parfois improprement appelée dépression unipolaire).\n"
                + "\n"
                + "Au travers des siècles, la connaissance de la nature et des causes de la dépression a évolué, bien que sa compréhension soit à ce jour incomplète et encore sujette à discussion. Les causes qui ont pu être proposées incluent des facteurs biologiques, psychologiques et psychosociaux ou environnementaux. L'utilisation à long terme et l'abus de certains médicaments et substances peuvent favoriser ou aggraver les symptômes dépressifs. Les psychothérapies peuvent se baser sur les théories de la personnalité, de la communication interpersonnelle, et de l'apprentissage. La plupart des théories biologiques se concentrent sur des neurotransmetteurs, des molécules naturellement présentes dans le cerveau qui permettent la communication chimique entre neurones. Les neurotransmetteurs de type monoaminergique comme la sérotonine, la noradrénaline et la dopamine sont plus particulièrement étudiés.");

        //this.soapMessage.setInfo("jerajoute de la merde \"Je suis secret\" qksrglqksgl");
//        this.soapMessage.setInfo(" \"Je suis secret\" \n"
//                + "Les patients sont habituellement traités avec un médicament antidépresseur, et dans certains cas suivent une psychothérapie. L'hospitalisation peut s'avérer nécessaire dans le cas d'auto-négligence, s'il existe un risque significatif de suicide ou pour la sécurité de l'entourage. Les dépressions résistantes aux traitements médicamenteux et à la psychothérapie peuvent être traitées par électroconvulsivothérapie ou par stimulation magnétique transcrânienne. La durée de la dépression est grandement variable, pouvant aller d'un épisode unique de quelques semaines à une longue période d'épisodes dépressifs prolongés et répétés (dans ce cas, il s'agit de dépression récurrente ou trouble unipolaire, parfois improprement appelée dépression unipolaire)."
//                + "Au travers des siècles, la connaissance de la nature et des causes de la dépression a évolué, bien que sa compréhension soit à ce jour incomplète et encore sujette à discussion. Les causes qui ont pu être proposées incluent des facteurs biologiques, psychologiques et psychosociaux ou environnementaux. L'utilisation à long terme et l'abus de certains médicaments et substances peuvent favoriser ou aggraver les symptômes dépressifs. Les psychothérapies peuvent se baser sur les théories de la personnalité, de la communication interpersonnelle, et de l'apprentissage. La plupart des théories biologiques se concentrent sur des neurotransmetteurs, des molécules naturellement présentes dans le cerveau qui permettent la communication chimique entre neurones. Les neurotransmetteurs de type monoaminergique comme la sérotonine, la noradrénaline et la dopamine sont plus particulièrement étudiés.");

        verifyTextIsCLear();
    }

    public void verifyTextIsCLear() throws SQLException {
        System.out.println(soapMessage.getInfo());
        String[] fileWords = soapMessage.getInfo().split("[ '.&\"(_)=)]");
        List<Boolean> resultList = new ArrayList();
        int trueOccurences = 0;
        float resultAccuracy = 0;

        for (int i = 0; i < fileWords.length; i++) {
            Boolean result = dao.getWordQuery(fileWords[i].toLowerCase());
            resultList.add(result);
            //System.out.println(result);
        }

        trueOccurences = Collections.frequency(resultList, true);
        resultAccuracy = (float) trueOccurences / (float) fileWords.length * 100;

        System.out.println("--------------------------");
        System.out.println(trueOccurences);
        System.out.println(fileWords.length);
        System.out.println(Math.round(resultAccuracy));
        System.out.println("--------------------------");

        if (resultAccuracy >= 75) {
            searchSecretMessage();
        }
    }

    private void searchSecretMessage() {

        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(soapMessage.getInfo());
        while (m.find()) {
            System.out.println(m.group(1));
        }
    }

}
