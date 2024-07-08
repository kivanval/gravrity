/*
Copyright 2024 Ivan Kyrylov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package io.github.kivanval.gravrity.util

import java.nio.file.Files
import java.nio.file.Path
import org.gradle.api.file.RelativePath
import org.gradle.internal.impldep.com.google.common.io.Resources
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.TempDir

class AvroFileVisitorTest extends Specification {

  @TempDir
  Path tmp

  def "file visitor collects all avro files"() {
    given:
    def project = ProjectBuilder.builder().build()
    def archive = Files.createFile(tmp.resolve('avro.zip'))
    def resPath = Path.of(Resources.getResource('nestedSample.zip').toURI())
    archive << Files.readAllBytes(resPath)

    def inputFiles = project.objects.fileTree().from(tmp)
    def avroVisitor = project.objects.newInstance(AvroFileVisitor)
    when:
    inputFiles.visit(avroVisitor)

    then:
    avroVisitor.targetFiles.size() == 6
    def relativePaths = avroVisitor.targetFiles.values()
    relativePaths.contains(new RelativePath(true, 'example', 'user_activity.avsc'))
    relativePaths.contains(new RelativePath(true, 'example', 'yelp_review.avsc'))
    relativePaths.contains(new RelativePath(true, 'example', 'truck_events.avsc'))
    relativePaths.contains(new RelativePath(true, 'user_profile.avsc'))
    relativePaths.contains(new RelativePath(true, 'user_activity.avsc'))
    relativePaths.contains(new RelativePath(true, 'page_view.avsc'))
  }
}
