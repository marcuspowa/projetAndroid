package utils;

import dagger.Module;
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
            UserManager.class,
            CacheManager.class,
            AsyncDataFetcher.class,
            SpotifyUtils.class
    }
)
public class MMQuizzModule {


}
