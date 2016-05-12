package utils;

import dagger.Module;
import models.Track;
import services.*;

/**
 * Created by remy on 08/03/2016.
 */
@Module(
    injects = {
            TrackManager.class,
            HttpUtils.class,
            QuestionManager.class,
            GenreManager.class,
            ArtistManager.class,
            AsyncDataFetcher.class
    }
)
public class MMQuizzModule {


}
