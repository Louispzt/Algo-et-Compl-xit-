package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws IOException {
        Bibliotheque bib = new Bibliotheque();
        if (!bib.isInitialized) {
            bib.Create();
        }
        Simplification simp = new Simplification();
        String str = "Des remèdes miracles aux théories du complot, la désinformation autour du nouveau coronavirus abonde sur les plateformes numériques, qui peinent à faire face tant les crises de cet ordre facilitent la propagation de fausses infos. De l’huile de sésame pour s’immuniser, des exodes de Chinois, un virus conçu par un laboratoire de Wuhan… Autant de fausses rumeurs qui se sont répandues ces dernières semaines sur les réseaux sociaux. « La majorité des acteurs à l’origine de ces fausses nouvelles se moque que vous y croyiez ou pas. Ils utilisent juste cette épidémie comme un vecteur idéal pour parvenir à leurs fins, qu’il s’agisse de générer des revenus ou de susciter la méfiance », note Carl Bergstrom, professeur à l’université de Washington et spécialiste de la désinformation en ligne. Certains acteurs veulent vendre des produits, et tentent de faire croire que le cannabis permet de s’immuniser contre le virus, par exemple. D’autres cherchent à générer des vues et des clics, sources de revenus publicitaires. « Et puis vous avez les opérations en cours pour affaiblir les démocraties, et donner l’impression qu’on ne peut faire confiance à personne », ajoute Carl Bergstrom. « C’est la stratégie du tuyau d’arrosage (inonder avec de la propagande, ndlr), fréquemment employée par la Russie, notamment ». Des responsables américains ont ainsi affirmé à l’AFP la semaine dernière que des milliers de comptes liés à la Russie sur Twitter, Facebook et Instagram propageaient de la désinformation anti-américaine sur le nouveau coronavirus, pour semer la discorde. Moscou a démenti. L’OMS prend des mesures Les théories disséminées incluent l’idée que le virus a été créé par les Etats-Unis rappelant les tentatives du KGB pour faire croire, pendant la Guerre froide, que le VIH était une invention de scientifiques américains. Une fois introduites, les théories du complot se répandent d’autant mieux que l’incertitude règne sur l’origine de la maladie et que le public s’inquiète et cherche des explications, de préférence sur les réseaux. Début février, l’OMS a qualifié d' »infodémie massive » la surabondance d’informations inexactes sur le sujet, qui complique sa tâche et celle des autorités de santé. Les fausses informations peuvent susciter des mouvements de panique, comme une ruée vers les masques chirurgicaux, des services d’urgence encombrés, ou, au contraire, des personnes présentant des symptômes qui préfèrent ne pas se signaler par peur de mesures de rétorsion imaginaires. Pour coordonner la lutte contre cette « infodémie », l’OMS a réuni il y a dix jours des représentants des grands groupes technologiques au siège de Facebook dans la Silicon Valley. Le géant du commerce en ligne Amazon a entrepris de retirer les produits qui prétendent, à tort, soigner le nouveau coronavirus ou protéger d’une infection, d’après la chaîne américaine CNBC. Facebook, Twitter et Google (dont YouTube) ont aussi renforcé leurs politiques existantes, qui consistent à retirer les contenus pouvant nuire physiquement au public, des publicités pour des faux remèdes dangereux, par exemple, et à mettre en avant les messages fiables (comme ceux de l’OMS). Une « hypocrisie » des plateformes Le réseau social dominant s’appuie aussi sur le « Third party fact-checking », son programme de vérification par des tiers développé depuis 2016. Facebook rémunère une soixantaine de médias à travers le monde, généralistes ou spécialisés -dont l’AFP- pour l’utilisation de leurs « fact-checks » sur sa plateforme et sur Instagram. Le réseau réduit fortement l’audience des vidéos ou articles étiquetés comme faux par ces vérificateurs externes. Mais Carl Bergstrom et Jevin West, co-auteurs d’un livre à paraître sur la désinformation, ne voient dans ces mesures que des « pansements ». Ils dénoncent « l’hypocrisie » des plateformes, dont les algorithmes et modèles économiques publicitaires favorisent la propagation des contenus sensationnalistes ou trompeurs. « C’est comme le jeu de la taupe (« whac-a-mole »), sauf qu’il y a un million de taupes qui sortent de tous les côtés et 5 personnes qui essaient de les écraser », remarque Jevin West, professeur en sciences informatiques. D’autres universitaires, qui ont étudié les phénomènes de désinformation lors des récentes crises du virus Zika et de fièvre jaune au Brésil estiment en outre que les efforts de fact-checking peuvent se révéler inopérants, voire contre-productifs. Dans leur étude publiée en janvier dans « Science Advances » ils concluent que « parler des fausses informations peut augmenter leur familiarité et les rendre encore plus plausibles ».";

        HashMap<String, Node> nodeHashMap = new HashMap<>();

        str = simp.simplifier(str);
        String[] phrases = str.split("&");
        int n = 0;
        for (int i = 0; i < phrases.length; i++) {
            String[] words = phrases[i].split(" ");
            for (int k = 0; k < words.length; k++) {
                if (words[k].length() > 2 && !bib.isUseless(words[k])) {
                    if (nodeHashMap.get(words[k]) == null){
                        nodeHashMap.put(words[k], new Node(words[k]));
                    }
                    else {
                        nodeHashMap.get(words[k]).increaseValue();
                        if (n < nodeHashMap.get(words[k]).getValue())
                            n = nodeHashMap.get(words[k]).getValue();
                    }
                }
            }
        }
        ArrayList<ArrayList<Node>> list = new ArrayList();
        for (int i = 0; i < n; i++)
            list.add(new ArrayList<>());
        for (String word : nodeHashMap.keySet()
        ) {
            list.get(nodeHashMap.get(word).getValue() - 1).add(nodeHashMap.get(word));
        }
        for (int i = 0; i < list.size(); i++){
            String strr = i + 1 + " : ";
            for (int k = 0; k < list.get(i).size(); k++)
                strr += (list.get(i).get(k).getString() + " ");
            System.out.println(strr);
        }
    }
}
