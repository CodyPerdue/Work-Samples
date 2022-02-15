--[[
    GD50
    Pokemon

    Author: Cody Perdue
    codyperdue88@hotmail.com
]]

BattleLevelState = Class{__includes = BaseState}

function BattleLevelState:init(pokemon)
	-- hold stats before level up
	local PreHP = pokemon.HP
	local PreAttack = pokemon.attack
	local PreDefense = pokemon.defense
	local PreSpeed = pokemon.speed

	-- trigger level up and store increase in values
	local HPUp, AttackUp, DefenseUp, SpeedUp = pokemon:levelUp()

    self.levelMenu = Menu {
        x = VIRTUAL_WIDTH - 160,
        y = VIRTUAL_HEIGHT - 160,
        width = 160,
        height = 96,
		useCursor = false,
        items = {
            {
                text = 'HP: ' .. PreHP .. ' + ' .. HPUp,
                onSelect = function() end
            },
			{
                text = 'Attack: ' .. PreAttack .. ' + ' .. AttackUp,
                onSelect = function() end
            },
			{
                text = 'Defense: ' .. PreDefense .. ' + ' .. DefenseUp,
                onSelect = function() end
            },
			{
                text = 'Speed: ' .. PreSpeed .. ' + ' .. SpeedUp,
                onSelect = function() end
            }
        }
    }
end

function BattleLevelState:update(dt)
    self.levelMenu:update(dt)
end

function BattleLevelState:render()
    self.levelMenu:render()
end
