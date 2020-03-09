package  com.example.veriparkapp.network;

import com.example.veriparkapp.model.detail.DetailBody;
import com.example.veriparkapp.model.detail.DetailModel;
import com.example.veriparkapp.model.handshake.HandshakeModel;
import com.example.veriparkapp.model.handshake.HandshakeReguestBody;
import com.example.veriparkapp.model.list.ListBody;
import com.example.veriparkapp.model.list.ListModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("api/handshake/start")
    Call<HandshakeModel> getFirstService (@Body HandshakeReguestBody body);



    @POST("api/stocks/list")
    Call<ListModel> getSecondListService (@Header("X-VP-Authorization") String authorization,
                                          @Header("Content-Type") String application,
                                          @Body ListBody body);

    @POST("api/stocks/detail")
    Call<DetailModel> getDetailService (@Header("X-VP-Authorization") String auth,@Header("Content-Type") String app,
                                        @Body DetailBody id);
}


