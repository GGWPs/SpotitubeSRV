package nl.han.oose;


import nl.han.oose.objects.Playlist;
import nl.han.oose.objects.Tracks;

import javax.inject.Inject;
import javax.naming.AuthenticationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistController {

    @Inject
    PlaylistService playlistService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylist(@QueryParam("token") String token) {
        try {
            return Response.ok().entity(playlistService.getPlaylists(token)).build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@QueryParam("token") String token, Playlist playlist) {
        try {
            return Response.status(Response.Status.OK).entity(playlistService.editPlaylist(token, playlist)).build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksFromPlaylist(@QueryParam("token") String token, @PathParam("id") final int id) {
        try {
            return Response.status(Response.Status.OK).entity(playlistService.getTracksFromPlaylist(token, id)).build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, Playlist playlist) {
        try {
            return Response.status(Response.Status.OK).entity(playlistService.addPlaylist(token, playlist)).build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int playlistId) {
        try {
            return Response.status(Response.Status.OK).entity(playlistService.deletePlaylist(token, playlistId)).build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("id") int playlistId, Tracks tracks) {
        try {
            return Response.status(Response.Status.OK).entity(playlistService.addTrackToPlaylist(token, playlistId, tracks)).build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    @Path("/{playlistId}/tracks/{trackId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrack(@QueryParam("token") String token, @PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId) {
        try {
            return Response.status(Response.Status.OK).entity(playlistService.deleteTrack(token, playlistId, trackId)).build();
        } catch (AuthenticationException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
