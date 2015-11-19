package bg.unisofia.fmi.videoapp.controller;

import bg.unisofia.fmi.videoapp.model.Video;
import bg.unisofia.fmi.videoapp.util.VideoEntryType;
import bg.unisofia.fmi.videoapp.util.VideoRootType;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.util.List;

public class AllVideosServlet extends HttpServlet {

    @Inject
    private EntityManager em;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse
            resp)
            throws ServletException, IOException {
        try {
            final VideoRootType reportRoot = new VideoRootType();
            final List<Video> allVideos = em.createQuery("select c from Video c").getResultList();
            for (final Video video : allVideos) {
                final VideoEntryType videoEntryType = new VideoEntryType();
                videoEntryType.setVideoId(String.valueOf(video.getId()));
                videoEntryType.setVideoName(video.getOriginalName());
                reportRoot.getList().add(videoEntryType);
            }
            final JAXBContext jaxbContext = JAXBContext.newInstance(VideoRootType.class);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            resp.setHeader("Content-Type", "XML");
            final ServletOutputStream os = resp.getOutputStream();
            jaxbMarshaller.marshal(reportRoot, os);


//            final Collection<License> licenses = JdoQueryBuilder.newQuery(pm,
//                    License.class, "").execute();
//            final LicenseReport export = new LicenseReport(month, licenses);
//            resp.setHeader("Content-Type", MimeType.XML.getMimeType());
//            final ServletOutputStream os = resp.getOutputStream();
//            try {
//                export.executeReportToOutputStream(os);
//            }
        } catch (JAXBException e) {
            //
        }
    }
}
