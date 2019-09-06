package com.jfilter.components;

import com.jfilter.filter.FileFilter;
import com.jfilter.mock.MockFile;
import com.jfilter.mock.config.WSConfigurationEnabled;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = WSConfigurationEnabled.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FileWatcherITest {
    private File file;
    private AtomicBoolean modified;
    private FileWatcher fileWatcher;
    private ScheduledAnnotationBeanPostProcessor taskScheduler;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void setTaskScheduler(ScheduledAnnotationBeanPostProcessor taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Autowired
    public FileWatcherITest setFileWatcher(FileWatcher fileWatcher) {
        this.fileWatcher = fileWatcher;
        return this;
    }

    @Before
    public void init() {
        modified = new AtomicBoolean(false);
        assertNotNull(fileWatcher);

        file = FileFilter.resourceFile("config.xml");
        assertNotNull(file);

        fileWatcher.add(file, (f) -> modified.set(true));
    }

    @Test
    public void testAdd() {
        boolean result = fileWatcher.add(file, (f) -> modified.set(true));
        assertTrue(result);
    }

    @Test
    public void testAddTwice() {
        boolean addedOne = fileWatcher.add(file, (f) -> modified.set(true));
        boolean addedTwo = fileWatcher.add(file, (f) -> modified.set(true));
        assertTrue(addedOne && addedTwo);
    }

    @Test
    public void testUnExistResourceFile() {
        boolean result = fileWatcher.add(FileFilter.resourceFile("un_exist_config.xml"), (f) -> modified.set(true));
        assertFalse(result);
    }

    @Test
    public void testUnExistFile() {
        boolean result = fileWatcher.add(new File("un_exist_config.xml"), (f) -> modified.set(true));
        assertFalse(result);
    }

    @Test
    public void testFileIsModifiedFalseInternally() {
        boolean result = fileWatcher.fileIsModified(file);
        assertFalse(result);
    }

    @Test
    public void testAfterIOException() {
        boolean add = fileWatcher.add(new MockFile("unknown_path"), (f) -> modified.set(true));
        assertFalse(add);
    }

    @Test
    public void testFileIsModified() {
        boolean result = fileWatcher.fileIsModified(new File(""));
        assertFalse(result);
    }

    @Test
    public void testClose() throws IOException {
        fileWatcher.destroy();
        assertTrue(fileWatcher.isClosed());
    }

    @Test
    public void testWatchSchedulerInterruptedException() {
        taskScheduler.destroy();
        await().atMost(10, SECONDS).until(() -> fileWatcher.isClosed());
        try {
            fileWatcher.scheduleModifiedFiles();
        } catch (Exception e) {
            e.printStackTrace();
            fileWatcher.scheduleModifiedFiles();
        }
        assertTrue(fileWatcher.isClosed());
    }

    @Test
    public void testWatchSchedulerClosedException() throws IOException {
        fileWatcher.getWatcher().close();
        await().atMost(5, SECONDS).until(() -> fileWatcher.isClosed());
        fileWatcher.scheduleModifiedFiles();
        assertTrue(fileWatcher.isClosed());

        fileWatcher.setClosed(false);
        fileWatcher.scheduleModifiedFiles();
        assertTrue(fileWatcher.isClosed());
    }


}
