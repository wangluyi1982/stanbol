/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.stanbol.contenthub.search.engines.ontologyresource;

import org.apache.stanbol.contenthub.servicesapi.search.vocabulary.SearchVocabulary;

import com.hp.hpl.jena.query.Query;

/**
 * 
 * @author cihan
 * 
 */
public final class QueryFactory {

    private static final String OWL = "PREFIX owl: <http://www.w3.org/2002/07/owl#>";
    // private static final String XSD = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
    // private static final String RDFS = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
    private static final String RDF = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    // private static final String FOAF = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>";
    // private static final String DC = "PREFIX dc: <http://purl.org/dc/elements/1.1/>";
    // private static final String DBPEDIA_RES = "PREFIX dbpedia1: <http://dbpedia.org/resource/>";
    // private static final String DBPEDIA_PROP = "PREFIX dbpedia2: <http://dbpedia.org/property/>";
    // private static final String DBPEDIA = "PREFIX dbpedia: <http://dbpedia.org/>";
    // private static final String SKOS = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>";
    private static final String PF = "PREFIX pf: <http://jena.hpl.hp.com/ARQ/property#>";
    private static final String SEARCH = "PREFIX ss: <" + SearchVocabulary.getUri() + ">";
    private static final String CMS = "PREFIX cms: <http://www.apache.org/stanbol/cms#>";

    public static final Query getClassQuery(String keyword) {

        StringBuilder sb = new StringBuilder();
        sb.append(RDF).append("\n");
        sb.append(OWL).append("\n");
        sb.append(PF).append("\n");
        sb.append(SEARCH).append("\n");
        sb.append("SELECT ?class ?score WHERE {\n");
        sb.append("\t?class rdf:type owl:Class.\n");
        sb.append("\t?class ss:hasLocalName ?name.\n");
        sb.append("\t(?name ?score) pf:textMatch '+" + normalizeKeyword(keyword) + "'.\n");
        sb.append("}");
        return com.hp.hpl.jena.query.QueryFactory.create(sb.toString());
    }

    public static final Query getIndividualQuery(String keyword) {
        StringBuilder sb = new StringBuilder();
        sb.append(RDF).append("\n");
        sb.append(OWL).append("\n");
        sb.append(PF).append("\n");
        sb.append(SEARCH).append("\n");
        sb.append("SELECT ?individual ?score WHERE {\n");
        sb.append("\t?individual rdf:type ?type.\n");
        sb.append("\t?type  rdf:type owl:Class.\n");
        sb.append("\t?individual ss:hasLocalName ?name.\n");
        sb.append("\t(?name ?score) pf:textMatch '+" + normalizeKeyword(keyword) + "'.\n");
        sb.append("}");
        return com.hp.hpl.jena.query.QueryFactory.create(sb.toString());
    }
    
    public static final Query getCMSObjectQuery(String keyword) {
        StringBuilder sb = new StringBuilder();
        sb.append(RDF).append("\n");
        sb.append(OWL).append("\n");
        sb.append(PF).append("\n");
        sb.append(SEARCH).append("\n");
        sb.append(CMS).append("\n");
        sb.append("SELECT ?cmsobject ?score WHERE {\n");
        sb.append("\t?cmsobject rdf:type cms:CMSObject.\n");
        sb.append("\t?cmsobject ss:hasLocalName ?name.\n");
        sb.append("\t(?name ?score) pf:textMatch '+" + normalizeKeyword(keyword) + "'.\n");
        sb.append("}");
        return com.hp.hpl.jena.query.QueryFactory.create(sb.toString());
    }

    private static String normalizeKeyword(String keyword) {
        return keyword.replace("'", "");
    }
}
