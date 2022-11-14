package org.demo.kml

import org.geotools.feature.DefaultFeatureCollection
import org.geotools.feature.simple.SimpleFeatureBuilder
import org.geotools.feature.simple.SimpleFeatureTypeBuilder
import org.geotools.kml.KMLConfiguration
import org.geotools.referencing.crs.DefaultGeographicCRS
import org.geotools.xsd.Parser
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.util.Assert
import org.opengis.feature.simple.SimpleFeature

internal class KmlDemoTest {

  @Test
  fun `geotools demo samples`() {
    val typeBuilder = SimpleFeatureTypeBuilder()
    typeBuilder.name = "points"
    typeBuilder.crs = DefaultGeographicCRS.WGS84
    typeBuilder.add("Geometry", Point::class.java)
    typeBuilder.add("blabla", Int::class.java)
    typeBuilder.add("age", Int::class.java)
    typeBuilder.add("stuff", String::class.java)

    val type = typeBuilder.buildFeatureType()
    val featureBuilder = SimpleFeatureBuilder(type)
    featureBuilder.add(GeometryFactory().createPoint(Coordinate(5.0, 2.0)))
    // respect properties order
    featureBuilder.add(32)
    featureBuilder.add(999)
    featureBuilder.add("hello world")

    val featureCollection = DefaultFeatureCollection("id-fc1", type)
    featureCollection.add(featureBuilder.buildFeature("id-f1"))

    val feature = featureCollection.features().next()
    println(feature.getProperty("age").value)
    println(feature.getProperty("stuff").value)
    println(feature.getProperty("blabla").value)
    println((feature.defaultGeometry as Point).coordinate)
  }

  @Test
  fun `geotools kml demo file`() {
    // https://docs.geotools.org/stable/userguide/extension/xsd/kml.html
    val stateFeature = readKml("/states.kml") as SimpleFeature
    println(stateFeature.getAttribute("name"))
    val collection = stateFeature.getAttribute("Feature") as Collection<*>
    Assert.equals(collection.size, 49)
  }

  private fun readKml(file: String) = Parser(KMLConfiguration()).parse(object{}.javaClass.getResourceAsStream(file))

}