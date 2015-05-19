package test;

import avis.SocialNetwork;

import java.util.HashMap;

interface SocialNetworkTest {
    HashMap<String, Integer> runTests(SocialNetwork sn, String pseudo1, String password1, String pseudo2, String password2) throws Exception;
}
