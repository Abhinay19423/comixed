/*
 * ComiXed - A digital comic book library management application.
 * Copyright (C) 2019, The ComiXed Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses>
 */

package org.comixedproject.rest.app;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertSame;

import java.text.ParseException;
import org.comixedproject.model.app.BuildDetails;
import org.comixedproject.model.net.app.LatestReleaseDetails;
import org.comixedproject.service.app.ReleaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ReleaseControllerTest {
  @InjectMocks private ReleaseController controller;
  @Mock private ReleaseService releaseService;
  @Mock private BuildDetails buildDetails;
  @Mock private LatestReleaseDetails latestReleaseDetails;

  @Test(expected = ParseException.class)
  public void testGetCurrentReleaseParsingException() throws ParseException {
    Mockito.when(releaseService.getCurrentReleaseDetails()).thenThrow(ParseException.class);

    try {
      controller.getCurrentRelease();
    } finally {
      Mockito.verify(releaseService, Mockito.times(1)).getCurrentReleaseDetails();
    }
  }

  @Test
  public void testGetCurrentRelease() throws ParseException {
    Mockito.when(releaseService.getCurrentReleaseDetails()).thenReturn(buildDetails);

    final BuildDetails result = controller.getCurrentRelease();

    assertNotNull(result);
    assertSame(buildDetails, result);

    Mockito.verify(releaseService, Mockito.times(1)).getCurrentReleaseDetails();
  }

  @Test
  public void testGetLatestRelease() {
    Mockito.when(releaseService.getLatestReleaseDetails()).thenReturn(latestReleaseDetails);

    final LatestReleaseDetails result = controller.getLatestRelease();

    assertNotNull(result);
    assertSame(latestReleaseDetails, result);

    Mockito.verify(releaseService, Mockito.times(1)).getLatestReleaseDetails();
  }
}
