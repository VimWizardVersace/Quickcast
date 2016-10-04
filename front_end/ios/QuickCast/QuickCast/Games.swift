//
//  Dota2Game.swift
//  QuickCast
//
//  Created by David Chen on 4/2/16.
//  Copyright © 2016 David Chen. All rights reserved.
//
//  These classes represents the model of a Dota2 Game
//

import Foundation
import UIKit

class Dota2Simple{
    var matchid: String
    var sport: String
    var numberOfGamesInMatch: Int?
    var team1stats: TeamStats
    var team2stats: TeamStats
    init(matchid:String, sport:String, team1:TeamStats, team2:TeamStats, numberOfGamesInMatch:Int? = 0){
        self.matchid = matchid
        self.sport = sport
        self.team1stats = team1
        self.team2stats = team2
    }
}

class TeamStats {
    var team1wins: Int
    var team2wins: Int
    
    
}

class Player {
    
    
}